AED_2
=====

Projecto AED 2

**Vamos ter 20!**
-------------

**Por Fazer**
- sub -> decrementa uma frequência de certo item (João)
- sink (Simão) Nao mexi muito aqui, o que fizeste acho que esta certo
- swim -> eleva o maximo que puder no amontoado (João)
- delMax() (João V Simão)
- Actualizar Javadoc, já está um pouco actualizado (João V Simão)
- Relatorio (João V Simão)


**nossos**
- reheap() (João V Simão)

------------------------------------------------------------------

**Métodos Feitos**

**Enunciado**
- topFreq -> maior frequencia em amontoado (Simão)
- topItem -> item com maior frequencia no amontoado (Simão)
- els -> devolve um bag com os elementos na queue (Simão)
- maxChild(int parent) -> recorre a compare
- sub(E item) (João) -> Iniciado c base em reheap
- add(E item) (João) se adiciona se ainda nao existir, testado.
- delMax() (João)
- swim(int n) -> iniciado
- sink(int n) -> (iniciei porque precisa para testar o delMax()) -> erro no sink

**Nossos**
- compare(int firstIndex, int secondIndex) (João) - agora compara os indexes, o codigo fica mais legivel
- reheap() -> iniciado. recorre ao swim (João)
- swap(int child, int parent) -> feito, a actualizar em map e em entries

**NOTA**
- Alterei o toString da frequencyqueue para perceber melhor o que se passa, depois e preciso por como deve ser
- Melhorar o delMax (nao precisa de se reestructurar o mapa porque e so para copias [see toString])
- Retirei o Reheap do Sub por agora, se algum no tiver frequencia de 1 entao eh uma folha, entao eh logo removido e nao eh necessario fazer reheap