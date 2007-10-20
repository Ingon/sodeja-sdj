; cons/car/cdr
(define a (cons 2 3))
(car a)
(cdr a)

; lets impl
(let ((z 10)) (+ z z))

; vectors
(define (make-vector x y) (cons x y))
(define (xcor p) (car p))
(define (ycor p) (cdr p))

; line segments
(define (make-seg p q) (cons p q))
(define (seg-start s) (car s))
(define (seg-end s) (cdr s))

(define (midpoint s)
  (let ((a (seg-start s))
        (b (seg-end s)))
    (make-vector
      (average (xcor a) (xcor b))
      (average (ycor a) (ycor b)))))

(define (length s)
  (let
    ((dx (- (xcor (seg-end s))
            (xcor (seg-start s))))
     (dy (- (ycor (seg-end s))
            (ycor (seg-start s)))))
    (sqrt (+ (square dx)
             (square dy)))))

; cons air
(define (cons-a a b)
  (lambda (pick)
    (cond ((= pick 1) a)
          ((= pick 2) b))))

(define (car-a x) (x 1))
(define (cdr-a x) (x 2))

(define ca (cons-a 5 7))
(car-a ca)
(cdr-a ca)
