package `in`.specmatic.core.wsdl.parser

import `in`.specmatic.core.git.log
import `in`.specmatic.core.pattern.XMLPattern
import `in`.specmatic.core.value.XMLNode
import `in`.specmatic.core.value.XMLValue

data class WSDLTypeInfo(val nodes: List<XMLValue> = emptyList(), val types: Map<String, XMLPattern> = emptyMap(), val namespacePrefixes: Set<String> = emptySet()) {
    fun getNamespaces(wsdlNamespaces: Map<String, String>): Map<String, String> {
        log.debug(wsdlNamespaces.toString())
        log.debug(namespacePrefixes.toString())

        return namespacePrefixes.toList().map {
            Pair(it, wsdlNamespaces.getValue(it))
        }.toMap()
    }

    val nodeTypeInfo: XMLNode
        get() {
            return XMLNode(TYPE_NODE_NAME, emptyMap(), nodes)
        }
}