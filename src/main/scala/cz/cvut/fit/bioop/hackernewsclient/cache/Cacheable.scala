package cz.cvut.fit.bioop.hackernewsclient.cache

trait Cacheable[T] {
  def toCacheable(elem: T): String
}