Projecto AED 2
=====

Vamos ter 20!
-------------

**Por Fazer**

- Fazer análise assintótica dos métodos (métodos não auxiliares não é preciso) (João)
- Relatorio (Simão)
- *Entregar até dia 25!*

------------------------------------------------------------------

## **Já feito**

- Implementação concluida
- Optimização de código, aka SCI (Sexy Code Implemented)
- Javadoc feito
- Monitorização Congu concluida sem erros


## **Métodos Feitos**

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
- swap(int child, int parent) -> feito, a actualizar em map e em entries

**NOTA**

```java
private void implementSexyCode() {
	...
	System.out.println("Sexy Code Implemented");
}
```