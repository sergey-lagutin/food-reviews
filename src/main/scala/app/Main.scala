package app

import scala.util.Try

object Main extends App {

  private def parseString(args: Array[String], key: String): Option[String] =
    args.find(_.startsWith(key))
      .map(_.split('=')(1))

  private def parseBoolean(args: Array[String], key: String, default: Boolean): Boolean =
    parseString(args, key)
      .flatMap(s => Try(s.toBoolean).toOption)
      .getOrElse(default)

  val filename = parseString(args, "filename").orNull

  val translate = parseBoolean(args, "translate", default = false)

  val statService = new StatService(filename)
  println(s"Most active users: ${statService.findMostActiveUsers(1000).sorted}")
  println(s"Most commented food items: ${statService.findMostCommentedFoodItems(1000).sorted}")
  println(s"Most user words: ${statService.findMostUserWords(1000).sorted}")
  statService.stop()

  if (translate) {
    new TranslateService(filename).run()
  }
}
