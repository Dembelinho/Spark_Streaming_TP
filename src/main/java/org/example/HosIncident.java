package org.example;


import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.concurrent.TimeoutException;

public class HosIncident {
    public static void main(String[] args) throws StreamingQueryException, TimeoutException {
        SparkSession ss= SparkSession.builder().appName("Structured Streaming")
                .master("local[*]").getOrCreate();

        // schema
        StructType structType= DataTypes.createStructType(new StructField[] {
                DataTypes.createStructField("Id",DataTypes.DoubleType,true),
                DataTypes.createStructField("Titre",DataTypes.StringType,true),
                DataTypes.createStructField("Description",DataTypes.StringType,true),
                DataTypes.createStructField("Service",DataTypes.StringType,true),
                DataTypes.createStructField("Date",DataTypes.DateType,true),
        });
        // Lire les données en streaming à partir des fichiers CSV
        Dataset<Row> streamingDF = ss.readStream()
                .schema(structType)
                .csv("C:/Work/Big Data/Spark_Streaming_TP/src/main/resources/incidents.csv");

        // Transformation : Agrégation du nombre d'incidents par service
        Dataset<Row> incidentsByService = streamingDF
                .groupBy("service")
                .agg(functions.count("Id").alias("nombre_incidents"));

        // Affichage du résultat en continu
        StreamingQuery query = incidentsByService
                .writeStream()
                .outputMode("complete")  // Mode de sortie : "complete" pour afficher toutes les données à chaque mise à jour
                .format("console")       // Format de sortie : console
                .start();

        // Attendre la fin de l'application
        query.awaitTermination();

    }
}
