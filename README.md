AED_2
=====

Projecto AED 2

**Vamos ter 20!**
-------------

**Por Fazer**
- topFreq -> maior frequencia em amontoado (Simão)
- topItem -> item com maior frquencia no amontoado (Simão)
- sub -> decrementa uma freqüência de certo item (João)
- elements -> devolve um bag com os elementos na queue (Simão)
- sink (Simão)
- swim -> eleva o maximo que puder no amontoado (João)
- maxChild -> melhorar
- delMax() (João V Simão)


**nossos**
- compare(Entry<E> first, Entry<E> second) (João)
- reheap() (João V Simão)
- swap(int child, int parent) (João)

------------------------------------------------------------------

**Métodos Feitos**

**Enunciado**
- maxChild(int parent) -> recorre a compare
- sub(E item) (João) -> Iniciado c base em reheap
- add(E item) (João) se adiciona se ainda nao existir, testado.
- delMax() (João)
- swim(int n) -> iniciado
- sink(int n) -> (iniciei porque precisa para testar o delMax()) -> erro no sink

**Nossos**
- compare(Entry<E> first, Entry<E> second) (João)
- reheap() -> iniciado. recorre ao swim (João)
- swap(int child, int parent) -> feito, a actualizar em map e em entries

**NOTA**
O map tem que ser reestructurado no sub e no delMax()