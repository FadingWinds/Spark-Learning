/*
val pi = 3.141592653
val double_pi = pi * 2
println(f"This is double pi: $double_pi%.3f")*/

/*for (x <- 1 to 4) {
  val number = x * x
  number match {
    case 4 => println("Four!")
    case _ => println("Not Four!")
  }
}*/

/*
var prev1 = 0
var prev2 = 1
for(x <- 1 to 8){
  val curr = prev1 + prev2
  println(curr)
  prev1 = prev2
  prev2 = curr
}*/

/*
val x : String = "I want to win this"
def UpperCaseConvert(y: String, f: String => String): String = {
  f(y)
}
UpperCaseConvert(x, x => x.toUpperCase)*/

var numberList = List(1)
for(x <- 2 to 20){
  numberList = x :: numberList
}
numberList = numberList.reverse
println(numberList)

numberList.filter(_%3==0).toList