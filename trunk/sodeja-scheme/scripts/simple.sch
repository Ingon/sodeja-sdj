;1
;(+ 1 3 (* 3 6))
;'s
;'()
;'(1 4)
;(define '() '(3 6))
;(define b 's)
;3
;(define b 3)
;b
;(define z1 (attach-type 't1 1))
;(define (sum-3 x y z) (+ x y z))
;(sum-3 1 2 3)

;(define (factorial n)
;  (if (= n 1)
;      1
;      (* (factorial (- n 1)) n)))
;
;(factorial 100)
;
;(define (fac n)
;  (define (iter product counter)
;    (if (> counter n)
;        product
;        (iter (* counter product)
;              (+ counter 1))))
;  (iter 1 1))
;  
;(fac 100)

(define (count n)
  (newline)
  (display n)
  (count (+ n 1)))

(count 0)
