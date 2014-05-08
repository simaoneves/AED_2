/**
 * The ADT specification for a Bag of elements
 *
 * A bag of elements, providing for:
 * 		adding elements to a bag;
 * 		removing an element from a bag;
 * 		checking whether the bag contains a given element.
 * Based on Paul E. Black, "bag", in Dictionary of Algorithms and Data Structures
 *
 * Project <A HREF = "http://gloss.di.fc.ul.pt/quest/"> Quest</A>.
 * 
 * @author antonialopes
*/

specification Bag[Element]
    sorts
        Bag[Element]
    constructors
        makeBag: --> Bag[Element];
        add: Bag[Element] Element --> Bag[Element];
    observers
        remove: Bag[Element] Element --> Bag[Element];
        howMany: Bag[Element] Element --> int;
        isEmptyBag: Bag[Element];
    others
        contains: Bag[Element] Element;
    axioms
        B: Bag[Element]; E, F: Element;

        remove (makeBag(), E) = makeBag();
        remove (add (B, E), F) = B when E = F else add(remove(B, F), E);

        howMany(makeBag(), E) = 0;
        howMany(add(B, E), F) = 1 + howMany(B,F) when E = F else howMany(B, F);

        isEmptyBag(makeBag());
        not isEmptyBag(add(B, E));

        contains(B, E) iff howMany(B, E) > 0;

        add(add(B, E), F) = add(add(B, F), E);
end specification

