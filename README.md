### Proje Özeti

Kafka Producer ve Kafka Consumer Oluşturma:
İlk adım, Kafka üzerinde bir topic oluşturmak ve bir Kafka producer ve consumer oluşturmaktır. Kafka producer, test verilerini Kafka topic'ine gönderecek olan yapıdır. Kafka consumer ise Spark Streaming tarafından verilerin alınacağı yapıdır.

Test Verilerinin Kafka Producer'a Gönderilmesi:
Test verileri, Kafka producer üzerinden satır satır Kafka topic'ine gönderilir. Bu veriler, genellikle bir formatta (JSON, CSV vb.) olabilir ve Kafka'ya gönderildikten sonra Kafka cluster'ında saklanır.

Spark Streaming ile Verilerin Alınması:
Spark Streaming, Kafka topic'inden gelen verileri okuyarak bir akış oluşturur. Bu akış, belirli aralıklarla yeni verileri işler. Spark, Kafka topic'inden gelen verileri almak için Kafka connector'larını kullanır.

Lojistik Regresyon Sınıflandırma Modelinin Eğitimi:
Spark, aldığı akış halindeki verileri işleyerek bir makine öğrenimi modeli olan lojistik regresyon sınıflandırma modelini eğitir. Bu model, verileri önceden tanımlanmış sınıflara göre sınıflandırmak için kullanılabilir.

Modelin Test Edilmesi:
Eğitilen model, Spark Streaming tarafından alınan akış halindeki verileri kullanarak test edilir. Model, yeni gelen verileri sınıflandırır ve tahminler yapar. Bu tahminler, gerçek sınıflandırmalarla karşılaştırılarak modelin doğruluğu değerlendirilir.

### Kullanılan Teknolojiler
##### 1. Spark:
Apache Spark, büyük veri analizi ve işleme için açık kaynaklı bir platformdur.
Hızlı, dağıtık ve genel amaçlı bir veri işleme motorudur. Paralel hesaplama yapabilme
yeteneğiyle büyük veri setlerini işleyebilir ve genellikle veri analitiği, makine
öğrenimi ve gerçek zamanlı işleme gibi alanlarda kullanılır.
##### 2. Spark Structured Streaming: 
Spark Structured Streaming, Apache Spark'ın bir
bileşenidir ve gerçek zamanlı veri işleme için kullanılır. Yapılandırılmış verileri
işleyebilme yeteneğiyle, veri akışlarını işlemek ve işlemek için bir API sağlar. Veriler
akış halindeyken işlenebilir, analiz edilebilir veya depolanabilir.
##### 3. Apache Kafka:
Apache Kafka, yüksek ölçeklenebilirlikte gerçek zamanlı veri akışı
sağlayan bir dağıtık olay akışı platformudur. Üreticilerden (producer) tüketicilere
(consumer) kadar olan veri akışını yönetir ve büyük ölçekli veri akışlarını depolamak,
işlemek ve entegre etmek için kullanılır.
##### 4. Scala: 
Scala, genel amaçlı bir programlama dilidir ve aynı zamanda Apache Spark
gibi büyük veri işleme sistemlerinde sıkça kullanılır. Fonksiyonel ve nesne yönelimli
programlama özelliklerini bir araya getirir. Scala, temiz ve esnek bir sözdizimine
sahiptir.
