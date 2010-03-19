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
  @ManyToOne
  var author : User
  
) extends Model {
  override def toString = title
}

object Post extends QueryOn[Post] {
  //extra finder methods here...
}