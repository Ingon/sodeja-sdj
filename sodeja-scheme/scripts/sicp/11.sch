; simple staff
3
(+ 3 4 8)
(+ 3 (* 5 6) 8 2)
(+ (* 3 5) (* 47 (- 20 6)) 12)

; value defineinition
(define a (* 5 5))
(* a a)
(define b (+ a (* 5 a)))
b
(+ a (/ b 5))

; function defineinition
(define squareLambda (lambda (x) (* x x)))
(define (square x) (* x x))
(square 10)
(square 1001)
(square (+ 5 7))
(+ (square 3) (square 4))
(square (square (square 1001)))

; some other functions
(define (abs x) (if (< x 0) (- x) x))
(define (average x y) (/ (+ x y) 2))
(define (mean-square x y) (average (square x) (square y)))

; sqrt plain
(define (improve guess x) (average guess (/ x guess)))
(define (good-enough? guess x) (< (abs (- (square guess) x)) 0.001))
(define (try guess x) (if (good-enough? guess x) guess (try (improve guess x) x)))
(define (sqrt1 x) (try 1 x))
(sqrt1 2)

; sqrt boxed
(define (sqrt x)
  (define (good-enough? guess) (< (abs (- (square guess) x)) 0.001))
  (define (improve guess) (average guess (/ x guess)))
  (define (try guess) (if (good-enough? guess) guess (try (improve guess))))
  (try 1.0))
(sqrt 2)
