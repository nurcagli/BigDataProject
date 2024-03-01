package SparkTemel

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.VectorAssembler

object KafkaConsumer {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    // SparkSession için ayarlar
    val master = "local[4]" // Yerel modda 4 çekirdek kullanımı
    val driverMemory = "3g" // Sürücü bellek ayarı
    val executorMemory = "6g" // Yürütücü bellek ayarı
    val appName = "KafkaConsumer" // Uygulama adı

    // SparkSession oluşturuluyor
    val spark = SparkSession.builder()
      .master(master)
      .config("spark.driver.memory", driverMemory)
      .config("spark.executor.memory", executorMemory)
      .appName(appName)
      .getOrCreate()


    // Kafka üzerinden akışlı bir DataFrame oluşturuluyor
    val dfstreaming = spark
      .readStream // Akışlı veri okuma işlemi başlatılıyor
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092") // Kafka sunucu adresi konfigüre ediliyor
      .option("subscribe", "denemekafka") // Abone olunacak Kafka konu adı belirleniyor
      .load() // Veriler yükleniyor
      .selectExpr("CAST(value AS STRING)") // kafka mesajından gelen 'value' sütununu STRING türüne dönüştürme işlemi yapılıyor

    // DataFrame üzerinde işlemler gerçekleştiriliyor
    val testDf = dfstreaming
      .selectExpr("split(value, ',') as array") // 'Value' sütununu virgül ile bölerek 'array' oluşturuluyor
      .selectExpr(
        "CAST(array[0] AS FLOAT) AS SepalLengthCm",
        "CAST(array[1] AS FLOAT) AS SepalWidthCm",
        "CAST(array[2] AS FLOAT) AS PetalLengthCm",
        "CAST(array[3] AS FLOAT) AS PetalWidthCm",
        "CAST(array[4] AS FLOAT) AS Species"
      )

    ///////////////////////// 3. Model Oluşturma /////////////////////////
    //iris setosa :0
    //Iris-versicolor : 1
    //Iris-virginica : 2

    // Dosyadan eğitim verisini okuma
    val trainDF = spark.read.format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .option("sep", ",")
      .load("D:\\Datasets\\irisTrain.csv")



    // 2.4. Vector Assembler Aşaması
    // Girdi nitelikleri tek bir vektör haline getirme
    val assembler = new VectorAssembler()
      .setInputCols(Array("SepalLengthCm", "SepalWidthCm", "PetalLengthCm", "PetalWidthCm"))
      .setOutputCol("features")

    // 3.1. Sınıflandırıcı nesnesi yaratma

    val logregObj = new LogisticRegression()
      .setLabelCol("Species") //  modelin öğrenmesi gereken hedef değişkeni içeren sütun
      .setFeaturesCol("features") // giriş özelliklerini içeren sütun

    // VectorAssembler ve LogisticRegression adımlarını içeren bir Pipeline oluşturma
    val pipeline = new Pipeline().setStages(Array( assembler, logregObj))

    // Pipeline ile veri setinin eğitilmesi
    val model = pipeline.fit(trainDF)

    // Eğitilmiş modeli test verisi üzerinde kullanma
    val result = model.transform(testDf)

    // Modelin tahmin sonuçlarının ekrana yazdırılması
    val query = result.writeStream
      .format("console") // Çıktı formatı olarak console kullanılıyor
      .outputMode("append") // Çıktı modu "Append" , her yeni veri parçasının akışa eklenmesini ve konsola yazılmasını sağlar.
      .start()

    query.awaitTermination() // Akışın sonlanmasını bekler

/*
    ///////////////////////// 4. Model Değerlendirme /////////////////////////


    import spark.implicits._

    // Burada DataFrame oluştururken yapılan işlemler...

    // Akış tamamlandığında tüm veriyi toplama
    val collectedData = dfstreaming
      .selectExpr("split(value, ',') as array")
      .selectExpr(
        "CAST(array[0] AS FLOAT) AS SepalLengthCm",
        "CAST(array[1] AS FLOAT) AS SepalWidthCm",
        "CAST(array[2] AS FLOAT) AS PetalLengthCm",
        "CAST(array[3] AS FLOAT) AS PetalWidthCm",
        "CAST(array[4] AS FLOAT) AS Species"
      )
      .as[(Float, Float, Float, Float, Float)] // Veriyi uygun bir tuple tipine dönüştürme

    // Veriyi DataFrame'e dönüştürme
    val allDataDF = collectedData.toDF("SepalLengthCm", "SepalWidthCm", "PetalLengthCm", "PetalWidthCm", "Species")

    //allDataDF.show()

    val evaluator = new MulticlassClassificationEvaluator()
      .setLabelCol("Species")
      .setPredictionCol("prediction")
      .setMetricName("accuracy")

    val accuracy = evaluator.evaluate(allDataDF)
    println(s"Model Accuracy on All Data: $accuracy")
*/





  }
}
