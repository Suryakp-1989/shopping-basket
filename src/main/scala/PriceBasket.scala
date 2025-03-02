import scala.collection.mutable

case class Item(name: String, price: BigDecimal)

object PriceBasket {

  val priceList: Map[String, BigDecimal] = Map(
    "Soup" -> 0.65,
    "Bread" -> 0.80,
    "Milk" -> 1.30,
    "Apples" -> 1.00
  )

  def calculateSubtotal(items: List[String]): BigDecimal = {
    items.flatMap(priceList.get).sum
  }

  def applyDiscounts(items: List[String]): (BigDecimal, List[String]) = {
    var totalDiscount: BigDecimal = 0
    val discountMessages = mutable.ListBuffer[String]()

    val appleCount = items.count(_ == "Apples")
    if (appleCount > 0) {
      val appleDiscount = appleCount * priceList("Apples") * 0.10
      totalDiscount += appleDiscount
      discountMessages += f"Apples 10%% off: £${appleDiscount}%.2f"
    }

    val soupCount = items.count(_ == "Soup")
    val breadCount = items.count(_ == "Bread")
    if (soupCount >= 2 && breadCount > 0) {
      val eligibleDiscounts = (breadCount min (soupCount / 2)) * (priceList("Bread") / 2)
      totalDiscount += eligibleDiscounts
      discountMessages += f"Bread half price: £${eligibleDiscounts}%.2f"
    }

    (totalDiscount, discountMessages.toList)
  }

  def main(args: Array[String]): Unit = {
    if (args.isEmpty) {
      println("Usage: PriceBasket item1 item2 item3 ...")
      return
    }

    val items = args.toList
    val subtotal = calculateSubtotal(items)
    val (discountTotal, discountMessages) = applyDiscounts(items)
    val totalPrice = subtotal - discountTotal

    println(f"Subtotal: £${subtotal}%.2f")
    if (discountMessages.isEmpty) println("(No offers available)")
    else discountMessages.foreach(println)
    println(f"Total price: £${totalPrice}%.2f")
  }
}
