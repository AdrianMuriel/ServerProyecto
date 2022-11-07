<?xml version='1.0' encoding="UTF-8"?>
<helpset version="2.0">
    <title>Java Development Environment − Help</title>
    	<maps>
        	<homeID>top </homeID>
            <mapref location="Mapa.jhm" />
		</maps>
        <view xml:lang="es" mergetype="javax.help.UniteAppendMerge">
        	<name>TOC</name>
            <label>Table Of Contents</label>
            <type>javax.help.TOCView</type>
            <data>tablaContenidos.xml</data>
		</view>
        <view xml:lang="es" mergetype="javax.help.SortMerge">
            <name>Index</name>
            <label>Index</label>
            <type>javax.help.IndexView</type>
            <data>indice.xml</data>
        </view>
        <view xml:lang="es">
                   <name>Search</name>
            <label>Search</label>
            <type>javax.help.SearchView</type>
            <data engine="com.sun.java.help.search.DefaultSearchEngine">
				JavaHelpSearch
			</data>
        </view>
		<view>
        	<name>favorites</name>
            <label>Favorites</label>
            <type>javax.help.FavoritesView</type>
        </view>
</helpset>