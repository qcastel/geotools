/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2007-2014, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotools.imageio.netcdf;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Locale;
import java.util.logging.Logger;

import javax.imageio.ImageReader;

import org.geotools.imageio.unidata.UnidataImageReaderSpi;
import org.geotools.imageio.unidata.utilities.UnidataUtilities;
import org.geotools.util.logging.Logging;

import ucar.nc2.dataset.NetcdfDataset;
import ucar.nc2.dataset.NetcdfDataset.Enhance;

/**
 * Service provider interface for the NetCDF Image
 * 
 * @author Alessio Fabiani, GeoSolutions
 */
public class NetCDFImageReaderSpi extends UnidataImageReaderSpi {

    static {
         NetcdfDataset.setDefaultEnhanceMode(EnumSet.of(Enhance.CoordSystems));
    }

    /** Default Logger * */
    private static final Logger LOGGER = Logging.getLogger(NetCDFImageReaderSpi.class);

    static final String[] suffixes;

    static final String[] formatNames;

    static final String[] MIMETypes;

    static final String version = "1.0";

    static final String readerCN = "it.geosolutions.imageio.plugins.netcdf.NetCDFImageReader";

    // writerSpiNames
    static final String[] wSN = { null };

    // StreamMetadataFormatNames and StreamMetadataFormatClassNames
    static final String nativeStreamMetadataFormatName = null;

    static final String nativeStreamMetadataFormatClassName = null;

    static final String[] extraStreamMetadataFormatNames = { null };

    static final String[] extraStreamMetadataFormatClassNames = { null };

    // ImageMetadataFormatNames and ImageMetadataFormatClassNames
    static final String[] extraImageMetadataFormatNames = { null };

    static final String[] extraImageMetadataFormatClassNames = { null };
 

    static{
        // If Grib Library is available, then the GRIB extension must be added to support
        if(UnidataUtilities.isGribAvailable()){
            suffixes  = new String[]{ "nc", "NC", "grib", "grb", "grb2" };
            formatNames = new String[]{ "netcdf", "NetCDF", "grib", "grib2", "GRIB", "GRIB2" };
            MIMETypes = new String[]{ "application/x-netcdf", "image/netcdf", "image/x-netcdf", "image/x-nc" , "application/octet-stream" };
        }else{
            suffixes  = new String[]{ "nc", "NC" };
            formatNames = new String[]{ "netcdf", "NetCDF" };
            MIMETypes = new String[]{ "application/x-netcdf", "image/netcdf", "image/x-netcdf", "image/x-nc" };
        }
    }

    
    /** Default Constructor * */
    public NetCDFImageReaderSpi() {
        super(
                version,
                formatNames,
                suffixes,
                MIMETypes,
                readerCN, // readerClassName
                wSN, // writer Spi Names
                nativeStreamMetadataFormatName,
                nativeStreamMetadataFormatClassName,
                extraStreamMetadataFormatNames,
                extraStreamMetadataFormatClassNames,
                extraImageMetadataFormatNames,
                extraImageMetadataFormatClassNames);

        LOGGER.fine("NetCDFImageReaderSpi Constructor");
    }

    /**
     * @see javax.imageio.spi.ImageReaderSpi#createReaderInstance(java.lang.Object)
     */
    @Override
    public ImageReader createReaderInstance(Object extension)
            throws IOException {
        return new NetCDFImageReader(this);
    }

    /**
     * @see javax.imageio.spi.IIOServiceProvider#getDescription(java.util.Locale)
     */
    @Override
    public String getDescription(Locale locale) {
        return new StringBuffer("NetCDF-CF Image Reader, version ").append(
                version).toString();
    }
}
