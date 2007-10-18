(define (sum term a next b)
  (if (> a b)
      0
      (+ (term a)
         (sum term (next a) next b))))

(define (inc x) (+ x 1))

(define (sum-int a b)
  (define (identity x) x)
  (sum identity a inc b))
  
(sum-int 3 5)

(define (sum-sq a b)
  (define (square x) (* x x))
  (sum square a inc b))
  
(sum-sq 3 5)

(define (pi-sum a b)
  (sum (lambda (i) (/ 1 (* i (+ i 2))))
       a
       (lambda (i) (+ i 4))
       b))

(* 8 (pi-sum 1 300))

(define (average x y) (/ (+ x y) 2))

(define (sqrt1 x)
  (fixed-point (lambda (y) (average (/ x y) y)) 1))
  
(define (abs x) (if (< x 0) (- x) x))

(define (fixed-point f start)
  (define tolerance 0.00001)
  (define (close-enuf? u v)
    (< (abs (- u v)) tolerance))
  (define (iter old new)
    (if (close-enuf? old new)
    new
    (iter new (f new))))
  (iter start (f start)))

(sqrt1 2)

(define (sqrt x)
  (fixed-point
    (average-damp (lambda (y) (/ x y)))
    1))

(define (average-damp f)
  (lambda (x) (average (f x) x)))

(sqrt 2)
