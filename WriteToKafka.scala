package SparkTemel
import java.util.Properties
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.spark.sql.{SparkSession, functions => F}
import org.apache.log4j.{Level, Logger}

object WriteToKafka {
  def main(args: Array[String]): Unit = {

    // LOG SEVİYESİNİ AYARLAMA
    Logger.getLogger("org").setLevel(Level.ERROR)

    // SparkSession için ayarlar
    val master = "local[4]" // Yerel modda 4 çekirdek kullanımı
    val driverMemory = "3g" // Sürücü bellek ayarı
    val executorMemory = "6g" // Yürütücü bellek ayarı
    val appName = "WriteToKafka" // Uygulama adı

    // SparkSession oluşturuluyor
    val spark = SparkSession.builder()
      .master(master)
      .config("spark.driver.memory", driverMemory)
      .config("spark.executor.memory", executorMemory)
      .appName(appName)
      .getOrCreate()

   //Test Verisi Dosyadan Okunuyor
    val TestDF = spark.read.format("csv")
      .option("header", true)
      .load("D:/Datasets/irisTest.csv")

    TestDF.show(1)



    // Kafkaya yazmak için hazıelık
    // Yeni bir sütun oluşturularak kolonlar birleştiriliyor ve 'value' adı verilen yeni sütuna atanıyor
    val MergedColumns = TestDF.withColumn("value",
      F.concat(F.col("SepalLengthCm"), F.lit(","),
        F.col("SepalWidthCm"), F.lit(","),
        F.col("PetalLengthCm"), F.lit(","),
        F.col("PetalWidthCm"), F.lit(","),
        F.col("Species")
      ))
      // Önceki sütunlar 'value' sütunu oluşturulduktan sonra düşürülüyor
      .drop("SepalLengthCm", "SepalWidthCm", "PetalLengthCm", "PetalWidthCm", "Species")


    // Kafka producer'ı yapılandırma
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")



    // TEST VERİLERİNİ SATIR SATIR KAFKAYA GÖNDERME

    //foreachPartition fonksiyonu, DataFrame'deki her bir bölüm (partition) için bir kez çağrılır
    MergedColumns.foreachPartition { partition =>
      // Kafka producer'ı oluşturma
        val producer = new KafkaProducer[String, String](props)

              // partition değişkeni, her bir bölümdeki veriye erişimi temsil eder.
              partition.foreach { row =>

                // DataFrame satırından değeri al
                val value = row.getAs[String]("value")

                // Kafka'ya mesaj gönder
                val kafkaMessage = new org.apache.kafka.clients.producer.ProducerRecord[String, String]("denemekafka", value)
                producer.send(kafkaMessage)

                // Her satır arasında 1.5 saniye bekleme
                Thread.sleep(2000)
              }
      producer.close()
    }


  }
}
