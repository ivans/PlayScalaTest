package models
 
import java.util._
import javax.persistence._
import play.db.jpa._
import play.data.validation._

@Entity
class Comment (
	@Required
	var author : String,
	@Required
    var postedAt : Date,
    @Lob
    @Required
    @MaxSize(10000)
    var content : String,
    @ManyToOne
    @Required
    var post : Post

) extends Model {
	
	def this(post : Post, author : String, content : String) {
		this(author, new Date, content, post)
	}

	override def toString = author + ": " + content
}

object Comment extends QueryOn[Comment] {
  //extra finder methods here...
}