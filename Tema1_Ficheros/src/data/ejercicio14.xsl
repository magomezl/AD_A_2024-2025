<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" encoding="UTF-16" />
    <!-- Solución con una estrategia iterativa -->
    <xsl:template match="/">
        <h1>Solución de examen de <xsl:value-of select="examen/materia"/></h1>
        <ul>
        <xsl:apply-templates select="examen/pregunta"/>
        </ul>
    </xsl:template>
    <xsl:template match="pregunta">
        <li><xsl:value-of select="enunciado"/>
            <ol type="a">
                <xsl:apply-templates select="respuesta"/>
            </ol>
        </li>
    </xsl:template>
    <xsl:template match="respuesta">
        <xsl:if test="@validez='si'"><li><u><xsl:value-of select="."/></u></li></xsl:if>
        <xsl:if test="@validez='no'"><li><xsl:value-of select="."/></li></xsl:if>
    </xsl:template>    
    
    
    
</xsl:stylesheet>
