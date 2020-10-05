package be.icteam.frameless

import frameless.{CatalystOrdered, _}

package object syntax {

  implicit class TypedDatasetOps[T](tds: TypedDataset[T]) {
    def tc[A](x: Function1[T, A]): TypedColumn[T, A] = macro TypedColumnMacro.impl[T, A]
  }

  implicit class TypedColumnOps[T, A](tc: TypedColumn[T, A]) {

    def count: TypedAggregate[T, Long] = {
      frameless.functions.aggregate.count(tc)
    }

    def countDistinct: TypedAggregate[T, Long] = {
      frameless.functions.aggregate.countDistinct(tc)
    }

    def approxCountDistinct: TypedAggregate[T, Long] = {
      frameless.functions.aggregate.approxCountDistinct[T](tc)
    }

    def approxCountDistinct(rsd: Double): TypedAggregate[T, Long] = {
      frameless.functions.aggregate.approxCountDistinct[T](tc, rsd)
    }

    def collectList(implicit ta: TypedEncoder[A]): TypedAggregate[T, Vector[A]] = {
      frameless.functions.aggregate.collectList[T, A](tc)
    }

    def collectSet(implicit ta: TypedEncoder[A]): TypedAggregate[T, Vector[A]] = {
      frameless.functions.aggregate.collectSet[T, A](tc)
    }

    def sum[Out](implicit summable: CatalystSummable[A, Out], aencoder: TypedEncoder[A], oencoder: TypedEncoder[Out]): TypedAggregate[T, Out] = {
      frameless.functions.aggregate.sum[A, T, Out](tc)
    }

    def sumDistinct[Out](implicit summable: CatalystSummable[A, Out], aencoder: TypedEncoder[A], oencoder: TypedEncoder[Out]): TypedAggregate[T, Out] = {
      frameless.functions.aggregate.sumDistinct[A, T, Out](tc)
    }

    def avg[Out](implicit averageable: CatalystAverageable[A, Out], oencoder: TypedEncoder[Out]): TypedAggregate[T, Out] = {
      frameless.functions.aggregate.avg[A, T, Out](tc)
    }

    def variance[Out](implicit averageable: CatalystAverageable[A, Out], oencoder: TypedEncoder[Out]): TypedAggregate[T, Out] = {
      frameless.functions.aggregate.avg[A, T, Out](tc)
    }

    def variance(implicit variance: CatalystVariance[A]): TypedAggregate[T, Double] = {
      frameless.functions.aggregate.variance[A, T](tc)
    }

    def stddev(implicit variance: CatalystVariance[A]): TypedAggregate[T, Double] = {
      frameless.functions.aggregate.stddev[A, T](tc)
    }

    def stddevPop(implicit ev: CatalystCast[A, Double]): TypedAggregate[T, Option[Double]] = {
      frameless.functions.aggregate.stddevPop[A, T](tc)
    }

    def stddevSamp(implicit ev: CatalystCast[A, Double]): TypedAggregate[T, Option[Double]] = {
      frameless.functions.aggregate.stddevSamp[A, T](tc)
    }

    def max(implicit ordered: CatalystOrdered[A]): TypedAggregate[T, A] = {
      frameless.functions.aggregate.max(tc)
    }

    def min(implicit ordered: CatalystOrdered[A]): TypedAggregate[T, A] = {
      frameless.functions.aggregate.min(tc)
    }

    def first: TypedAggregate[T, A] = {
      frameless.functions.aggregate.first(tc)
    }

    def last: TypedAggregate[T, A] = {
      frameless.functions.aggregate.last(tc)
    }

    def corr[B](column2: TypedColumn[T, B])(implicit i0: CatalystCast[A, Double], i1: CatalystCast[B, Double]): TypedAggregate[T, Option[Double]] = {
      frameless.functions.aggregate.corr[A, B, T](tc, column2)
    }

    def covarPop[B](column2: TypedColumn[T, B])(implicit i0: CatalystCast[A, Double], i1: CatalystCast[B, Double]): TypedAggregate[T, Option[Double]] = {
      frameless.functions.aggregate.covarPop[A, B, T](tc, column2)
    }

    def covarSamp[B](column2: TypedColumn[T, B])(implicit i0: CatalystCast[A, Double], i1: CatalystCast[B, Double]): TypedAggregate[T, Option[Double]] = {
      frameless.functions.aggregate.covarSamp[A, B, T](tc, column2)
    }

    def kurtosis(implicit ev: CatalystCast[A, Double]): TypedAggregate[T, Option[Double]] = {
      frameless.functions.aggregate.kurtosis[A, T](tc)
    }
  }

}
