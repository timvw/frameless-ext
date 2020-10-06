package be.icteam.frameless

import frameless.TypedDataset
import frameless.TypedEncoder._
import org.apache.spark.sql.SparkSession
import org.scalatest.funsuite.AnyFunSuite
import be.icteam.frameless.syntax._

case class TestClass2(c: String, x: Int)
case class TestClass1(a: Int, b: TestClass2)
class packageTest extends AnyFunSuite {

  implicit val spark: SparkSession =  SparkSession
      .builder()
      .master("local[*]")
      .appName("test")
      .getOrCreate()

    test("check tc syntaxt"){
    val numbers = Range(1,10).toList.map(i => TestClass1(i, TestClass2(i.toString, i)))

    val td: TypedDataset[TestClass1] = TypedDataset.create(numbers)

      assert( td.filter(td.tc(_.b.x) > 2).dataset.collect().length == 7)
  }
}

