x = 5
y = 3

L1:	if x < y goto L4
	t1 = true
	goto L5
L4:	t1 = false
L5:	r = t1
L3:	iffalse x == y goto L7
	t2 = true
	goto L8
L7:	t2 = false

L2:
