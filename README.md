# Frameless-ext

This library contains additional syntax for [Frameless](https://github.com/typelevel/frameless).

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

The available aggregation functions become more discoverable in your IDE as well:

```scala
val result: TypedDataset[(String, Long, Int)] = tds.
  .groupBy(e.tds(_.user))
  .agg(
    tds.tc(_.day).countDistinct,
    tds.tc(_.hour).max)
```

Here is the complete example:


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
      Event("tiebe", 2020, 9, 1, 2)
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
sbt +clean; +cleanFiles; +compile; +test
```

Install a snapshot in your local maven repository:

```bash
sbt +publishM2
```

## Release

Set the following environment variables:
- PGP_PASSPHRASE
- PGP_SECRET
- SONATYPE_USERNAME
- SONATYPE_PASSWORD

Leveraging the [ci-release](https://github.com/olafurpg/sbt-ci-release) plugin:

```bash
sbt ci-release
```

Find the most recent release:

```bash
git ls-remote --tags $REPO | \
  awk -F"/" '{print $3}' | \
  grep '^v[0-9]*\.[0-9]*\.[0-9]*' | \
  grep -v {} | \
  sort --version-sort | \
  tail -n1
```

Push a new tag to trigger a release via [travis-ci](https://travis-ci.org/github/timvw/frameless-ext):

```bash
v=v1.0.5
git tag -a $v  -m $v
git push origin $v
```