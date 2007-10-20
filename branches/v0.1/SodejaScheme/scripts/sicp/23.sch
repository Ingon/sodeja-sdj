; SELECTORS
; (real z)
; (imag z)
; (mag z)
; (angle z)
; CONSTRUCTORS
; (make-rect x y)
; (make-polar r a)

(define (+c z1 z2)
  (make-rect
    (+ (real z1) (real z2))
    (+ (imag z1) (imag z2))))

(define (-c z1 z2)
  (make-rect
    (- (real z1) (real z2))
    (- (imag z1) (imag z2))))

(define (*c z1 z2)
  (make-rect
    (* (mag z1) (mag z2))
    (+ (angle z1) (angle z2))))

(define (/c z1 z2)
  (make-rect
    (/ (mag z1) (mag z2))
    (- (angle z1) (angle z2))))

; Rect
(define (make-rect x y) (cons x y))

(define (real z) (car z))

(define (imag z) (cdr z))

(define (make-polar r a)
  (cons (* r (cos a)) (* r (sin a))))

(define (mag z)
  (sqrt (+ (square (car z))
           (square (cdr z)))))

(define (angle z)
  (atan (cdr z) (car z)))

; Polar

; Typed data
(define (attach-type type contents)
  (cons type contents))

(define (type datum)
  (car datum))
  
(define (contents datum)
  (cdr datum))

; Tests

;(define z1 (make-rect 1 1))

;(define z2 (make-rect 2 2))

;(+c z1 z2)

(define z1 (attach-type 't1 1))

(define z2 (attach-type 't2 2))

(define (test-dispatch z)
  (cond ((eq? (type z) 't1) (contents z))
        ((eq? (type z) 't2) (contents z))
        (else 'error)))

(test-dispatch z1)
(test-dispatch z2)
