;(define a (* 3 4))
;a
;(set! a (* 5 5))
;a
;(define b 's)
;b
;(define b '(a b))
;
;(define count 1)
;(define (demo x)
;  (set! count (+ 1 count))
;  (+ x count))
;  
;(demo 3)
;
;(demo 3)
;
;(define (fact n)
;  (let ((i 1) (m 1))
;    (define (loop)
;      (cond ((> i n) m)
;            (else
;              (set! m (* i m))
;              (set! i (+ i 1))
;              (loop))))
;    (loop)))
;
;(fact 6)
;

(define (make-counter n) (lambda () (set! n (+ 1 n)) n))

(define c1 (make-counter 0))

(define c2 (make-counter 10))

(c1)
(c2)
(c1)
(c2)
