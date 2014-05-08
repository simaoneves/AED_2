specification  FreqQueue[Element]
  sorts
    FreqQueue[Element]
  constructors
    makeFQ: --> FreqQueue[Element];
    addFQ: FreqQueue[Element] Element --> FreqQueue[Element];
  observers
    isEmpty: FreqQueue[Element];
    sub: FreqQueue[Element] Element --> FreqQueue[Element];
   others 
    //auxiliary operation
    //not part of the adt but is useful for specifying its operations
    els:  FreqQueue[Element] --> Bag[Element];   
    topElement: FreqQueue[Element] -->? Element;
    topFreq: FreqQueue[Element] -->? int;  
  domains
    Q: FreqQueue[Element];
    topElement (Q) if not isEmpty (Q);
    topFreq (Q) if not isEmpty (Q);
  axioms
    Q: FreqQueue[Element];  E, F: Element;
 
    isEmpty(makeFQ ());
    not isEmpty(addFQ(Q, E));
    
    els(makeFQ ()) = makeBag ();
    els(addFQ (Q, E)) = add (els(Q), E);
  
    //this ADT does not completely specifies what is the resut of sub
 	sub(makeFQ(), E) = makeFQ();
    sub(addFQ(Q, E), F) = Q if E = F;
    sub(addFQ(Q, E), F) = addFQ(Q, E) if isEmpty (Q) and not (E = F);
    sub(addFQ(Q, E), F) = addFQ(sub(Q,F), E) if not isEmpty (Q) and not (E = F);
    //sub(addFQ(Q, E), F)= ? if not isEmpty (Q) and E = F
    //this is constrained by
    els (sub(Q,E)) = remove (els(Q), E);
       
    //this ADT does not completely specify what is the result of topElement
    topElement(addFQ(Q, E)) = E if isEmpty (Q); 
    topElement(addFQ(Q, E)) = E if not isEmpty (Q) and topElement(Q) = E; 
    topElement(addFQ(Q, E)) = E if not isEmpty (Q) and 
    		topElement(Q) != E and topFreq(Q) - howMany(els (Q),E) = 0; 
    topElement(addFQ(Q, E)) = topElement(Q) if not isEmpty (Q) and 
    		topFreq(Q) - howMany(els (Q),E) > 1; 
    //topElement(addFQ(Q, E))= ? if not isEmpty (Q) and 
    //		topFreq(Q) - freq(Q,E) = 1
    //this is constrained by
    topFreq(Q) = howMany(els (Q), topElement(Q)) if not isEmpty (Q);      
 end specification
