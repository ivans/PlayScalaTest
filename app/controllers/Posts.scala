package controllers

import play._
import play.mvc._
import models._

object Posts extends Controller with CRUDFor[Post]