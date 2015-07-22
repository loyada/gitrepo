package dollarx.examples

import dollarx.ElementProperties.has
import dollarx.InBrowser.click
import dollarx.Path._

/**
 * Created by danny on 6/13/2015.
 */
object printDSL extends App {

  val dialog = div withClass "ui-dialog"
  val row = has cssClass "condition" inside dialog after div

  // logging:
  println(row withText("John"))

  // further enhancement
  val condRow = row describedBy "condition row"
  val expr = has cssClass "binary-expr" describedBy "the awesome binary expression"

  println (expr inside condRow(2) withClass "foo" )

  val condRow2 = row(2) describedBy "the second condition"
  println (expr after condRow2 )

  println(div withText("rain") or (span withClass("fog")))
}
