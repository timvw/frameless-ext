package be.icteam.frameless

import frameless.TypedColumn

import scala.language.experimental.macros
import scala.reflect.macros.whitebox

object TypedColumnMacro {

  def apply[T, A](x: Function1[T, A]): TypedColumn[T, A] = macro impl[T, A]

  def impl[T: c.WeakTypeTag, A: c.WeakTypeTag](c: whitebox.Context)(x: c.Tree) = {

    import c.universe._

    val t = c.weakTypeOf[T]
    val a = c.weakTypeOf[A]

    def buildExpression(columnName: String) = {
      c.Expr[TypedColumn[T, A]](q"new frameless.TypedColumn[$t, $a]((org.apache.spark.sql.functions.col($columnName)).expr)")
    }

    x match {
      case q"((${_: TermName}:${_: Type}) => ${_: TermName}.${p: TermName})" => buildExpression(p.toString())
      case q"(_.${p: TermName})" => buildExpression(p.toString())
      case x => throw new IllegalArgumentException(s"$x is not supported")
    }
  }

}