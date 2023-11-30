package org.example;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;

import java.util.concurrent.TimeoutException;

public class Appli2 {
    public static void main(String[] args) throws TimeoutException, StreamingQueryException {
        SparkSession ss= SparkSession.builder().appName("Structured Streaming")
                .master("local[*]").getOrCreate();
        Dataset<Row> inputTable= ss.readStream().format("socket")
                .option("port",8080).option("host","localhost")
                .load();

        StreamingQuery query=inputTable.writeStream().format("console").
                outputMode("append").start();
        query.awaitTermination();

      //  Dataset<String> dfWords =inputTable.as(Encoders.STRING()).flatMap()
    }
}
