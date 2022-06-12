package universidadean.programacionfuncional

import ean.collections.IList
import kotlin.math.pow
import kotlin.math.sqrt

// Esta clase guarda la información de un producto de una tienda
data class Producto(val codigo: Int, val nombre: String, val cantidad: Int, val precio: Int)

// Esta clase guarda la información de un departamento del país
data class Departamento(
    val nombre: String,
    val poblacion: Int,
    val superficie: Double,
    val densidad: Double,
    val IDH: Double,
    val añoCreacion: Int)

// Esta clase guarda la información de un municipio del pais
data class Municipio(
    val codigo: Int,
    val nombre: String,
    val departamento: String,
    val poblacionUrbana: Int,
    val poblacionRural: Int,
    val esCapital: Boolean
)

// Esta clase guarda la información de un rectángulo
data class Rectangulo(val base: Double, val altura: Double) {
    // Hallar el área del rectangulo
    fun area(): Double = base * altura
}

// Esta clase guarda la información de un triángulo
data class Triangulo(val id: Int,
                     val lado1: Double,
                     val lado2: Double,
                     val lado3: Double)

//-------------------------------------------------------------------
// Operaciones con la clase Departamento
//-------------------------------------------------------------------

/**
 * Obtener el nombre del departamento más antiguo de toda la lista.
 * Si la lista está vacía, retorne null
 */
fun metodo6(dptos: IList<Departamento>): String? {
    if(dptos.isEmpty)
        return null

    var depAntiguo = dptos.minWith(compareBy(Departamento::añoCreacion))
    return depAntiguo!!.nombre
}

/**
 * Retorna el  departamento que tiene la superficie más grande
 * pero con una población superior a la población que se pasa
 * como parámetro.
 */
fun metodo7(dptos: IList<Departamento>, poblacion: Int): Departamento? {
    val pobla = dptos.filter {it.poblacion > poblacion }
    var superficie = pobla.maxWith(compareBy(Departamento::superficie))
    return superficie
}

/**
 * Retorne la lista de los nombres de los departamentos creados
 * en el siglo XX y que tenga un IDH entre 0.85 y 0.95
 */
fun metodo8(dptos: IList<Departamento>): IList<String> {
    var creados = dptos.filter { it.añoCreacion in 1901 .. 2000 && it.IDH in 0.85 .. 0.95}
    var nombres = creados.map { it.nombre }
    return nombres


}

/**
 * Retorne el porcentaje de departamentos de la lista cuya densidad
 * esté por debajo del valor que se pasa como parámetro
 */
fun metodo9(deptos: IList<Departamento>, valor: Double): Double {
    val cantidad = deptos.count()
    val porcentaje = deptos.count { it.densidad < valor }
    return 100*porcentaje.toDouble()/cantidad

}

/**
 * Retorne el promedio de superficie de los departamentos de la lista
 * cuya poblacion sea superior a la población del departamento con menor
 * IDH de toda la lista
 */
fun metodo10(deptos: IList<Departamento>): Double {
    val menorIDH = deptos.minWith(compareBy(Departamento::IDH))
    val lista = deptos.filter { it.poblacion > menorIDH!!.poblacion }
    val suma = lista.map { it.superficie }.sum()
    val cantidadLista = lista.count()
    return suma/cantidadLista.toDouble()

}

//-------------------------------------------------------------------
// Operaciones con la clase Municipio
//-------------------------------------------------------------------

/**
 * Determinar y retornar cuántos municipios de la lista son capitales
 */
fun metodo11(muns: IList<Municipio>): Int {
    return muns.count { it.esCapital }
}

/*
 * Determinar el nombre del municipio que no es capital y que pertenece al
 * departamento que se recibe como parámetro y que tiene la población urbana
 * más grande
 */
fun metodo12(m: IList<Municipio>, depto: String): String {
    return m.filter { !it.esCapital && it.departamento == depto}
        .maxWith(compareBy(Municipio::poblacionUrbana))!!.nombre
}

/**
 * Retornar el promedio de la población total (suma de la población rural y población urbana)
 * de aquellos municipios de la lista que pertenecen al departamento que se pasa
 * como parámetro y cuyo código sea múltiplo de 3 o de 5
 */
fun metodo13(municipios: IList<Municipio>, departamento: String): Double {
    var lista = municipios.filter { it.departamento == departamento && (it.codigo.mod(3)==0 || it.codigo.mod(5)==0)}
    var sumaPoblaciones = lista.map { it.poblacionRural + it.poblacionUrbana }.sum()
    return sumaPoblaciones/lista.count().toDouble()
}

/**
 * Retorne el nombre del primer municipio que inicia con J en toda la lista
 */
fun metodo14(muns: IList<Municipio>): String {
    return muns.filter { it.nombre.startsWith("J") }.first.nombre
}


/**
 * Retorne cuantos municipios de la lista que tienen un código
 * de 4 dígitos poseen una poblacion rural superior a la población
 * urbana
 */
fun metodo15(muns: IList<Municipio>): Int {
    TODO("Completar")
}

//-------------------------------------------------------------------
// Operaciones con la clase Producto
//-------------------------------------------------------------------

/*
 * Obtener el nombre de todos los productos cuyo código es par
 */
fun metodo1(productos: IList<Producto>): IList<String> {
    return productos.filter { it.codigo%2 == 0 }.map { it.nombre }

}

/**
 * Obtener cuántos productos tienen un precio inferior al producto
 * cuyo código se pasa como parámetro
 */
fun metodo2(productos: IList<Producto>, codProducto: Int): Int {
    val valor = productos.filter { it.codigo == codProducto }.map { it.precio }.first
    return productos.filter { it.precio < valor }.count()
}

/**
 * Obtener una lista con los códigos de los productos cuya cantidad sea
 * superior a la cantidad mínima que se pasa como parámetro y cuyo precio
 * esté entre mil y diez mil pesos.
 *
 */
fun metodo3(productos: IList<Producto>, cantidadMinima: Int): IList<Int> {
    return productos.filter { it.cantidad > cantidadMinima && it.precio in 1000 .. 10000 }.map { it.codigo }
}

/**
 * EL inventario total de la lista es la suma de la multiplicación de la cantidad por el precio
 * de todos y cada uno de los productos de la lista. Este método permite saber si el
 * inventario de la lista es superior al millón de pesos o no.
 */
fun metodo4(prods: IList<Producto>): Boolean {
    return prods.map { it.precio * it.cantidad }.sum() > 1000000
}

/**
 * Obtener el promedio de la cantidad de aquellos productos cuyo precio
 * esté por debajo del promedio de precio de todos los productos de la lista
 */
fun metodo5(prods: IList<Producto>): Double {
    val promedio = prods.map { it.precio }.sum()/prods.count()
    val lista = prods.filter { it.precio<promedio }
    return lista.map { it.cantidad }.sum()/lista.count().toDouble()
}

//-------------------------------------------------------------------
// Operaciones con la clase Producto
//-------------------------------------------------------------------

fun metodo16(rects: IList<Rectangulo>): Int {
    return rects.filter { it.altura == it.base }.count()
}

fun metodo17(rects: IList<Rectangulo>): Double {
    val menor = rects.filter { it.base < it.altura }
    return menor.map { it.base*it.altura }.sum()/menor.count()
}

fun metodo18(rects: IList<Rectangulo>): Rectangulo {
    TODO("Obtener el rectangulo de mayor área de la lista")

}

fun metodo19(rects: IList<Rectangulo>, areaMin: Double): IList<Double> {
    TODO("Lista con las diagonales de aquellos cuadrados cuya area sea superior al area que se pasa como parámetro")
}

/**
 * Un triangulo es rectangulo si un lado (el mas largo) es igual a la raiz cuadrada de
 * la suma de los cuadrados de los otros dos lados
 */
fun esRectangulo(t: Triangulo): Boolean {
    TODO("Retorna true si t es triangulo rectangulo")
}

fun areaTriangulo(t: Triangulo): Double {
    TODO("Hallar el área del triángulo a partir de la fórmula de Herón")
}

fun metodo20(triangulos: IList<Triangulo>): IList<Double> {
    TODO("Lista de áreas de aquellos triangulos rectangulos de la lista")
}

fun metodo21(triangulos: IList<Triangulo>): IList<Int> {
    fun esIsosceles(t: Triangulo) = t.lado1 == t.lado2 || t.lado2 == t.lado3 || t.lado1 == t.lado3
    //TODO("Obtener la lista de identificadores de aquellos triangulos isosceles cuya área no supere 10")
    return triangulos.filter { esIsosceles(it) && areaTriangulo(it) <= 10.0 }.map { it.id }
}