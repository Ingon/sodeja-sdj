; simple staff
(define (factorialR n)
  (if (= n 1)
      1
      (* n (factorialR (- n 1)))))

(factorialR 6)

(define (factorialI n)
  (fact-iter 1 1 n))

(define (fact-iter product counter max-count)
  (if (> counter max-count)
      product
      (fact-iter (* counter product)
                 (+ counter 1)
                 max-count)))

(factorialI 6)

(define (ackermann x y)
  (cond ((= y 0) 0)
        ((= x 0) (* 2 y))
        ((= y 1) 2)
        (else (ackermann (- x 1)
                 (ackermann x (- y 1))))))

(ackermann 1 10)

(define (fibR n)
  (cond ((= n 0) 0)
        ((= n 1) 1)
        (else (+ (fibR (- n 1))
                 (fibR (- n 2))))))

(fibR 10)

(define (fibI n)
  (fib-iter 1 0 n))

(define (fib-iter a b count)
  (if (= count 0)
      b
      (fib-iter (+ a b) a (- count 1))))

(fibI 10)
