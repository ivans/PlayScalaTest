import play.test._

import org.junit._
import org.scalatest.junit._
import org.scalatest._
import org.scalatest.matchers._ 

import java.util.Date

import models._

class DaoTests extends UnitTest with ShouldMatchersForJUnit {

	@Before
	def setup() {
		Fixtures.deleteAll()
	}
	
	@Test
	def createUser() {
		val user = new User("bob@gmail.com", "secret", "Bob", "Bla", false)
		user.saveThis()
		val bob = User.find("byEmail", "bob@gmail.com").first
		bob should not be (null)
		bob.firstName should be ("Bob")
		bob.lastName should be ("Bla")
	}

	@Test
	def createPost() {
		val user = new User("bob@gmail.com", "secret", "Bob", "Bla", false)
		user.saveThis() 
		val post = new Post("Test title", new Date, "Neki sadržaj posta.", user)
		post.saveThis()
		
		Post.count should be (1)
		 
		val lista = Post.find("byAuthor", user).fetch
		lista.size should be (1)
	}

	@Test
	def createComment() {
		val user = new User("bob@gmail.com", "secret", "Bob", "Bla", false)
		user.saveThis
		val post = new Post("Test title", new Date, "Neki sadržaj posta.", user)
		post.saveThis
		
		val comment1 = new Comment(post, "Jeff", "Nice post")
		val comment2 = new Comment(post, "Tom", "I knew that !")
		
		comment1.saveThis
		comment2.saveThis

		val postComments = Comment.find("byPost", post).fetch
		postComments.size should be (2)
	}
}
