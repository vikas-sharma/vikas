<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/photo">

		<div class="container">

			<div class="left-element">
				<xsl:for-each select="family/information">
					<div id="photo_{photoId}">
						<a href="#" onclick="loadAlbum({photoId});return false;">
							<xsl:value-of select="title" />
						</a>
					</div>
				</xsl:for-each>
			</div>

			<div class="right-element">

				<xsl:for-each select="family/information">
					<div id="album_{photoId}">
						<embed type="application/x-shockwave-flash"
							src="http://picasaweb.google.com/s/c/bin/slideshow.swf" width="600"
							height="400" flashvars="{flashvars}"
							pluginspage="http://www.macromedia.com/go/getflashplayer"></embed>
					</div>
				</xsl:for-each>
			</div>
		</div>

	</xsl:template>
</xsl:stylesheet>