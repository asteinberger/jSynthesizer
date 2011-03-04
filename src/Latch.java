/**
 * Adapted from code by Neil Coffey.
 * 
 * Here, we are implementing a simple latch class in Java - there are built-in
 * latch operations to do the same thing, but this shows you how you can use
 * wait() and notify() to communicate between threads.
 * 
 * Internally, all we have is a simple counter that decrements until all 
 * threads have completed their work, at which point notify() is called to
 * unblock the wait() operation.  This is more economical than polling, 
 * which wastes CPU, etc.
 * 
 * Also, note that the latch synchronizes on an arbitrary object - this is
 * one of the requirements of synchronization (must synchronize on an object
 * as opposed to a value e.g. a primitive data type).
 * 
 * GNU General Public License v3, February 2011.
 * 
 * @author Adam Steinberger
 *
 */

public class Latch {
	
	private final Object synchObj = new Object();
	private int count;
	
	public Latch(int numThreads) {
		synchronized (synchObj) {
			this.count = numThreads;
		}
	}
  
  // call this on the main thread while the background threads
  // complete their work
  public void awaitZero() throws InterruptedException {
    synchronized (synchObj) {
      while (count > 0) {
        synchObj.wait();
      }
    }
  }
  
  // call this when a thread completes its work
  public void countDown() {
    synchronized (synchObj) {
      if (--count <= 0) {
        synchObj.notifyAll();
      }
    }
  }
}
