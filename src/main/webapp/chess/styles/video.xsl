<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:param name="gid" />

	<xsl:template match="/">

		<div class="container">

			<div class="left-element">

				<H3>Select a Game:</H3>

				<xsl:for-each select="video/information">

					<a href="iccVideos.htm?gameId={gameId}">
						<xsl:value-of select="title" />
					</a>
					<BR />
				</xsl:for-each>

			</div>

			<div class="right-element">

				<xsl:for-each select="video/information">

					<xsl:choose>
						<xsl:when test="gameId = $gid">
							<object width="640" height="385">
								<param name="movie" value="{url}"></param>
								<param name="allowFullScreen" value="true"></param>
								<param name="allowscriptaccess" value="always"></param>
								<embed src="{url}" type="application/x-shockwave-flash"
									allowscriptaccess="always" allowfullscreen="true" width="640"
									height="385"></embed>
							</object>
						</xsl:when>
					</xsl:choose>

				</xsl:for-each>
			</div>
		</div>

	</xsl:template>
</xsl:stylesheet>