/*
package com.sundogsoftware.spark

import org.apache.log4j._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{round, sum}
import org.apache.spark.sql.types.{DoubleType, IntegerType, StructType}

/** Compute the total amount spent per customer in some fake e-commerce data. */
object TotalSpentByCustomerSortedDataset {

  case class CustomerOrders(cust_id: Int, item_id: Int, amount_spent: Double)

  /** Our main function where the action happens */
  def main(args: Array[String]) {
   
    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession
      .builder
      .appName("TotalSpentByCustomer")
      .master("local[*]")
      .getOrCreate()

    // Create schema when reading customer-orders
    val customerOrdersSchema = new StructType()
      .add("cust_id", IntegerType,nullable = true)
      .add("item_id", IntegerType,nullable = true)
      .add("amount_spent", DoubleType,nullable = true)

    // Load up the data into spark dataset
    // Use default separator (,), load schema from customerOrdersSchema and force case class to read it as dataset
    import spark.implicits._
    val customerDS = spark.read
      .schema(customerOrdersSchema)
      .csv("data/customer-orders.csv")
      .as[CustomerOrders]

    val totalByCustomer = customerDS
      .groupBy("cust_id")
      .agg(round(sum("amount_spent"), 2)
        .alias("total_spent"))

    val totalByCustomerSorted = totalByCustomer.sort("total_spent")
    
    totalByCustomerSorted.show(totalByCustomer.count.toInt)
  }
  
}

*/

package com.sundogsoftware.spark

import org.apache.spark.sql.SparkSession
import org.apache.log4j._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{StructType, IntegerType, StringType, FloatType}

object TotalSpentByCustomerSortedDataset {

  case class customer(id: Int, order: String, spent: Float)

  def main(args: Array[String]) {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession
      .builder()
      .appName("TotalSpent")
      .master("local[*]")
      .getOrCreate()

    val newSchema = new StructType()
      .add("id", IntegerType)
      .add("order", StringType)
      .add("spent", FloatType)

    import spark.implicits._

    val customerSpent = spark.read
      .schema(newSchema)
      .csv("data/customer-orders.csv")
      .as[customer]

    val totalSpent = customerSpent
      .select("id", "spent")
      .groupBy("id")
      .sum("spent")

    totalSpent.show()

    val sortedTS = totalSpent
      .withColumnRenamed("sum(spent)", "totalSpent")
      .withColumn("totalSpent", round($"totalSpent", scale = 2))
      .sort("totalSpent")

    sortedTS.show()

    spark.stop()

  }
}