package controllers

import play._
import play.mvc._
import models._

object Tags extends Controller with CRUDFor[Tag]