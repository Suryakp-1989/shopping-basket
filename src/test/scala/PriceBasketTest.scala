import org.scalatest.funsuite.AnyFunSuite

class PriceBasketTest extends AnyFunSuite {

  test("Calculate subtotal correctly") {
    val subtotal = PriceBasket.calculateSubtotal(List("Apples", "Milk", "Bread"))
    println(f"Subtotal: £$subtotal%.2f")
    assert(subtotal == 3.10)
  }

  test("Apply apple discount correctly") {
    val (_, discounts) = PriceBasket.applyDiscounts(List("Apples"))
    println(s"Discounts applied: ${discounts.mkString(", ")}")
    assert(discounts.contains("Apples 10% off: £0.10"))
  }

  test("Apply soup-bread discount correctly") {
    val (_, discounts) = PriceBasket.applyDiscounts(List("Soup", "Soup", "Bread"))
    println(s"Discounts applied: ${discounts.mkString(", ")}")
    assert(discounts.contains("Bread half price: £0.40"))
  }

  test("No discounts when only one soup is bought") {
    val (_, discounts) = PriceBasket.applyDiscounts(List("Soup"))
    println(s"Discounts applied: ${discounts.mkString(", ")}")
    assert(discounts.isEmpty)
  }

  test("Multiple discounts applied") {
    val (_, discounts) = PriceBasket.applyDiscounts(List("Soup", "Soup", "Bread", "Apples"))
    println(s"Discounts applied: ${discounts.mkString(", ")}")
    assert(discounts.contains("Apples 10% off: £0.10"))
    assert(discounts.contains("Bread half price: £0.40"))
  }

  test("Bread discount applies only once") {
    val (_, discounts) = PriceBasket.applyDiscounts(List("Soup", "Soup", "Bread", "Bread"))
    println(s"Discounts applied: ${discounts.mkString(", ")}")
    assert(discounts.contains("Bread half price: £0.40"))
  }

  test("Extra soup does not provide extra discount") {
    val (_, discounts) = PriceBasket.applyDiscounts(List("Soup", "Soup", "Soup", "Bread"))
    println(s"Discounts applied: ${discounts.mkString(", ")}")
    assert(discounts.contains("Bread half price: £0.40"))
  }

  test("Subtotal with no discounts") {
    val subtotal = PriceBasket.calculateSubtotal(List("Milk"))
    println(f"Subtotal: £$subtotal%.2f")
    assert(subtotal == 1.30)
  }

  test("Empty basket results in zero subtotal") {
    val subtotal = PriceBasket.calculateSubtotal(List())
    println(f"Subtotal: £$subtotal%.2f")
    assert(subtotal == 0.00)
  }
}
