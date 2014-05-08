<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<xsl:for-each select="favourite/head">
			<H3>
				<xsl:value-of select="@name" />
			</H3>
			<xsl:for-each select="information">
				<a href="{url}" target="_blank">
					<xsl:value-of select='title' />
				</a>
				<BR />
			</xsl:for-each>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>