package org.example.TP;


import org.apache.spark.SparkConf;
import org.apache.spark.sql.*;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

public class test {
    public static void main(String[] args) throws InterruptedException {
        SparkConf conf=new SparkConf().setAppName("TP Streaming").setMaster("local[*]");
        JavaStreamingContext sc =new JavaStreamingContext(conf, Duration.apply(10000));
        JavaDStream<String> fileStream = sc.textFileStream("hdfs://localhost:9000/mon");
        // Convertir chaque ligne du fichier en un objet Incident
        JavaDStream<Incident> incidents = fileStream.map(line -> parseCSV(line));

        // Utiliser Spark Structured Streaming pour effectuer le traitement
        incidents.foreachRDD(rdd -> {
            SparkSession spark = SparkSession.builder().config(rdd.context().getConf()).getOrCreate();

            // Convertir le RDD d'Incidents en DataFrame
            Dataset<Row> incidentsDF = spark.createDataFrame(rdd, Incident.class);
            incidentsDF.show();
            // Effectuer le traitement pour afficher le nombre d'incidents par service
            incidentsDF.groupBy("service").count().show();
        });

        sc.start();
        sc.awaitTermination();
    }

    // Fonction pour convertir une ligne CSV en objet Incident
    private static Incident parseCSV(String line){
        String[] parts = line.split(",");
        return new Incident(parts[0],
                parts[1],
                parts[2],
                parts[3],
                parts[4]
                );
    }
}
