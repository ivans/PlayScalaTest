import play.test._

import org.junit._
import org.scalatest.junit._
import org.scalatest._
import org.scalatest.matchers._ 

import models._

class DaoTests extends UnitTest with FlatSpec with ShouldMatchers {

  "Creating a user" should "be succesfull" in {
    val user = new User("bob@gmail.com", "secret", "Bob", "Bla", false)
    user.saveThis()
    val bob = User.find("byEmail", "bob@gmail.com").first
    bob should not be (null)
    bob.firstName should be ("Bob")
	bob.lastName should be ("Bla")
  }
}
