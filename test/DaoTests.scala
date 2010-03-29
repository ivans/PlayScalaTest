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
		val user = new User("bob@gmail.com", "secret", "Bob", "Bla")
		user.saveThis()
		val bob = User.find("byEmail", "bob@gmail.com").first
		bob should not be (null)
		bob.firstName should be ("Bob")
		bob.lastName should be ("Bla")
	}

	@Test
	def createPost() {
		val user = new User("bob@gmail.com", "secret", "Bob", "Bla")
		user.saveThis() 
		val post = new Post(user , "Test title", "Neki sadržaj posta.")
		post.saveThis()
		
		Post.count should be (1)
		 
		val lista = Post.find("byAuthor", user).fetch
		lista.size should be (1)
	}

	@Test
	def createComment() {
		val user = new User("bob@gmail.com", "secret", "Bob", "Bla")
		val post = new Post(user, "Test title", "Neki sadržaj posta.")
		
		post addComment new Comment(post, "Jeff", "Nice post")
		post addComment new Comment(post, "Tom", "I knew that !")
		
		post.addComment("Pero", "komentar 3")
		post.saveThis

		val postComments = Comment.find("byPost", post).fetch
		postComments.size should be (3)
	}
	
	@Test
	def testoviNaLoadanimPodacima() {
		Fixtures.load("data.yml");

	    // Count things
		User.count should be (2)
		Post.count should be (3)
		Comment.count should be (3)
	}
	
	@Test
	def testTag() {
		val tag1 = models.Tag.findOrCreateByName("abc")
		tag1 should not be (null)
		tag1.id should be (null)
		
		val tag2 = new models.Tag("cde")
		tag2.saveThis
		
		val tag3 = models.Tag.findOrCreateByName("cde")
		tag3 should not be (null)
		tag3.id should not be (null)
	}

	@Test
	def testPostWithTags() {
		val user = new User("bob@gmail.com", "secret", "Bob", "Bla")
		val post = new Post(user , "Test title", "Neki sadržaj posta.")
		post isTaggedWith "test" should be (false)
		post.tagItWith("test")
		post isTaggedWith "test" should be (true)
		post isTaggedWith "test2" should be (false)
		post.saveThis
		Post.findTaggedWith("test").length should be (1)
	}
}
