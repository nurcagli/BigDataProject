Proje Özeti
 Kafka producer ve kafka consumer yapılarının oluşturulması ve kafka producera satır satır
gönderilen test verilerinin kafka consumer olan spark tarafından streaming şekilde alınması.
Daha sonra lojistik regresyon sınıflandırma algoritmasıyla modelin eğitilmesi ve spark
streaming ile alınan akış halindeki verinin test verisi olarak kullanılarak model testinin
yapılması işlemleri gerçekleştirildi.
Kullanılan Teknolojiler
1. Spark: Apache Spark, büyük veri analizi ve işleme için açık kaynaklı bir platformdur.
Hızlı, dağıtık ve genel amaçlı bir veri işleme motorudur. Paralel hesaplama yapabilme
yeteneğiyle büyük veri setlerini işleyebilir ve genellikle veri analitiği, makine
öğrenimi ve gerçek zamanlı işleme gibi alanlarda kullanılır.
2. Spark Structured Streaming: Spark Structured Streaming, Apache Spark'ın bir
bileşenidir ve gerçek zamanlı veri işleme için kullanılır. Yapılandırılmış verileri
işleyebilme yeteneğiyle, veri akışlarını işlemek ve işlemek için bir API sağlar. Veriler
akış halindeyken işlenebilir, analiz edilebilir veya depolanabilir.
3. Apache Kafka: Apache Kafka, yüksek ölçeklenebilirlikte gerçek zamanlı veri akışı
sağlayan bir dağıtık olay akışı platformudur. Üreticilerden (producer) tüketicilere
(consumer) kadar olan veri akışını yönetir ve büyük ölçekli veri akışlarını depolamak,
işlemek ve entegre etmek için kullanılır.
4. Scala: Scala, genel amaçlı bir programlama dilidir ve aynı zamanda Apache Spark
gibi büyük veri işleme sistemlerinde sıkça kullanılır. Fonksiyonel ve nesne yönelimli
programlama özelliklerini bir araya getirir. Scala, temiz ve esnek bir sözdizimine
sahiptir.
