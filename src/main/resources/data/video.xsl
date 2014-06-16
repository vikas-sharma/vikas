<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:param name="gid" />

	<xsl:template match="/">

		<div class="container">

			<div class="left-element">

				<H3>Select a Game:</H3>

				<xsl:for-each select="video/information">

					<xsl:choose>
						<xsl:when test="gameId = $gid">
							<div style="background-color:gray;">
								<a href="iccVideos.htm?gameId={gameId}">
									<xsl:value-of select="title" />
								</a>
							</div>
						</xsl:when>
						<xsl:otherwise>
							<div>
								<a href="iccVideos.htm?gameId={gameId}">
									<xsl:value-of select="title" />
								</a>
							</div>
						</xsl:otherwise>
					</xsl:choose>

				</xsl:for-each>
			</div>

			<div class="right-element">
				<object width="640" height="385">
					<param name="movie" value="{/video/information[gameId=$gid]/url}"></param>
					<param name="allowFullScreen" value="true"></param>
					<param name="allowscriptaccess" value="always"></param>
					<embed src="{/video/information[gameId=$gid]/url}" type="application/x-shockwave-flash"
						allowscriptaccess="always" allowfullscreen="true" width="640"
						height="385"></embed>
				</object>
			</div>
		</div>

	</xsl:template>
</xsl:stylesheet>