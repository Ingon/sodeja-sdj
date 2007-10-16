; simple staff
(def (factorialR n)
  (if (= n 1)
      1
      (* n (factorialR (- n 1)))))

(factorialR 6)

(def (factorialI n)
  (fact-iter 1 1 n))

(def (fact-iter product counter max-count)
  (if (> counter max-count)
      product
      (fact-iter (* counter product)
                 (+ counter 1)
                 max-count)))

(factorialI 6)

(def (ackermann x y)
  (cond ((= y 0) 0)
        ((= x 0) (* 2 y))
        ((= y 1) 2)
        (else (ackermann (- x 1)
                 (ackermann x (- y 1))))))

(ackermann 1 10)

(def (fibR n)
  (cond ((= n 0) 0)
        ((= n 1) 1)
        (else (+ (fibR (- n 1))
                 (fibR (- n 2))))))

(fibR 10)

(def (fibI n)
  (fib-iter 1 0 n))

(def (fib-iter a b count)
  (if (= count 0)
      b
      (fib-iter (+ a b) a (- count 1))))

(fibI 10)
