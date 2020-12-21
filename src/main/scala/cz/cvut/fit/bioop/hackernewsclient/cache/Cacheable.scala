package cz.cvut.fit.bioop.hackernewsclient.cache

/**
 * Common trait for elements that can be cached
 */
trait Cacheable[T] {
  def toCacheable(elem: T): String
}