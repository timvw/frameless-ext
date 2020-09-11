# Frameless-ext

This library contains additional syntax for [Frameless]()https://github.com/typelevel/frameless.

## Usage

Enable the additional syntax with the following import statement:

```scala
import be.icteam.frameless.syntax._
```

And now you can create TypedColumns via a simple lambda on a TypeDataSet, eg:

```scala
val tds: TypedDataset[Event] = ???

// the compiler can infer the types ;)
val userColumn = tds.tc(_.user)
val dayColumn = tds.tc(_.day)
```

We have also added support for aggregate functions on the typed columns,
as you can see in the following example:

```scala
case class Event(user: String, year: Int, month: Int, day: Int, hour: Int)

object Demo {

  import frameless._
  import frameless.syntax._
  import frameless.functions.aggregate._
  import be.icteam.frameless.syntax._
  import org.apache.log4j.{Level, LogManager}
  import org.apache.spark.sql.SparkSession

  def initSpark: SparkSession = {

    LogManager.getLogger("org").setLevel(Level.ERROR)

    SparkSession
      .builder()
      .appName("demo")
      .master("local[*]")
      .config("spark.ui.enabled", "false")
      .getOrCreate()
  }

  def main(args: Array[String]): Unit = {

    implicit val spark = initSpark

    import spark.implicits._

    val events = spark.createDataset(List(
      Event("tim", 2020, 9, 1, 7),
      Event("tim", 2020, 9, 1, 3),
      Event("tim", 2020, 9, 2, 5),
      Event("tim", 2020, 9, 2, 3),
      Event("narayana", 2020, 9, 1, 2)
    ))

    val e = TypedDataset.create(events)

    val result: TypedDataset[(String, Long, Long, Int, Long)] = e
      .groupBy(e.tc(_.user))
      .agg(
        count[Event](),
        e.tc(_.day).countDistinct,
        e.tc(_.hour).max,
        e.tc(_.year).sum)
    val job = result.show(10, false)
    job.run()

  }
}
```

## Development

Compile and test:

```bash
sbt -Dsbt.log.noformat=true +clean +cleanFiles +compile +test
```

Creating a development version:

```bash
sbt publishM2
```

## Release

Make sure to have a ~/.sbt/1.0/sonatype.sbt with your credentials, eg:

```scala
credentials += Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", "timvw", "My very secret password")% 
```

```bash
sbt
+clean ;+compile ;+test ;+package; +publishSigned ; sonatypeReleaseAll
ci-release
```

export PGP_PASSPHRASE=""
export LONGID=0x6E4CD7D2EAC83E19
export PGP_SECRET=$(gpg --armor --export-secret-keys $LONGID | base64)
export SONATYPE_USERNAME=timvw
export SONATYPE_PASSWORD=Tvw12345

Via travis-ci:

```bash
git tag -a v1.0.3  -m v1.0.3
git push origin v1.0.3
```