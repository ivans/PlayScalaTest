package models
 
import java.util._
import javax.persistence._
import play.db.jpa._
import play.data.validation._

@Entity
class Post (

	@Required
	var title : String,
	@Required
	var postedAt : Date,
	@Lob 
	@Required
	@MaxSize(10000)
	var content : String,
	@Required
	@ManyToOne(cascade=Array(CascadeType.ALL))
	var author : User,
	@OneToMany(mappedBy="post", cascade=Array(CascadeType.ALL))
	var comments : List[Comment] = new ArrayList

) extends Model {
	def this(author : User, title : String, content : String) {
		this(title, new Date, content, author, new ArrayList)
	}

	def addComment(newComment : Comment) : Post = {
		comments.add(newComment)
		this
	}

	def addComment(author : String, content : String) : Post = {
		addComment(new Comment(this, author, content))
		this
	}

	override def toString = title
}

object Post extends QueryOn[Post] {
	//extra finder methods here...
}