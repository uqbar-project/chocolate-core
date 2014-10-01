import scala.language.implicitConversions

object WS {

	trait R {
		def m = 6
	
		def apply(task: =>Unit) = {
			task
		}
	}
	
	def f(n: Int)(implicit r: R) = println(n + r.m)
                                                  //> f: (n: Int)(implicit r: WS.R)Unit
	def g(n: Int)(implicit r: R) = println(n + " " + r.m)
                                                  //> g: (n: Int)(implicit r: WS.R)Unit

	object DO extends R {
	}
	implicit val r: R = DO                    //> r  : WS.R = WS$$anonfun$main$1$DO$2$@3add750e
	
	
	DO {
		f(5)
		g(5)
	}                                         //> 11
                                                  //| 5 6
}