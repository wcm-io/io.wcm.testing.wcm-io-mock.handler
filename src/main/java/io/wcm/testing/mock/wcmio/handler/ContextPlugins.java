/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2016 wcm.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package io.wcm.testing.mock.wcmio.handler;

import org.apache.sling.testing.mock.osgi.context.AbstractContextPlugin;
import org.apache.sling.testing.mock.osgi.context.ContextPlugin;
import org.jetbrains.annotations.NotNull;

import io.wcm.handler.link.impl.DefaultLinkHandlerConfig;
import io.wcm.handler.link.impl.ImageMapLinkResolverImpl;
import io.wcm.handler.link.impl.LinkHandlerAdapterFactory;
import io.wcm.handler.media.format.impl.MediaFormatProviderManagerImpl;
import io.wcm.handler.media.impl.DefaultMediaHandlerConfig;
import io.wcm.handler.media.impl.MediaHandlerAdapterFactory;
import io.wcm.handler.mediasource.dam.impl.dynamicmedia.DynamicMediaSupportServiceImpl;
import io.wcm.handler.mediasource.dam.impl.metadata.AssetSynchonizationService;
import io.wcm.handler.mediasource.dam.impl.metadata.RenditionMetadataListenerService;
import io.wcm.handler.richtext.impl.DefaultRichTextHandlerConfig;
import io.wcm.handler.url.impl.DefaultUrlHandlerConfig;
import io.wcm.handler.url.impl.SiteRootDetectorImpl;
import io.wcm.handler.url.impl.UrlHandlerAdapterFactory;
import io.wcm.handler.url.impl.clientlib.ClientlibProxyRewriterImpl;
import io.wcm.testing.mock.aem.context.AemContextImpl;

/**
 * Mock context plugins.
 */
public final class ContextPlugins {

  private ContextPlugins() {
    // constants only
  }

  /**
   * Context plugin for wcm.io Handler
   */
  public static final @NotNull ContextPlugin<AemContextImpl> WCMIO_HANDLER = new AbstractContextPlugin<AemContextImpl>() {
    @Override
    public void afterSetUp(@NotNull AemContextImpl context) throws Exception {
      setUp(context);
    }
  };

  /**
   * Set up all mandatory OSGi services for wcm.io Handler support.
   * @param context Aem context
   */
  static void setUp(AemContextImpl context) {

    // url handler
    context.registerInjectActivateService(SiteRootDetectorImpl.class);
    context.registerInjectActivateService(UrlHandlerAdapterFactory.class);
    context.registerInjectActivateService(ClientlibProxyRewriterImpl.class);
    context.registerInjectActivateService(DefaultUrlHandlerConfig.class);

    // media handler
    context.registerInjectActivateService(MediaHandlerAdapterFactory.class);
    context.registerInjectActivateService(DefaultMediaHandlerConfig.class);
    context.registerInjectActivateService(MediaFormatProviderManagerImpl.class);

    // media handler rendition metadata listener service
    context.registerInjectActivateService(AssetSynchonizationService.class);
    context.registerInjectActivateService(RenditionMetadataListenerService.class,
        "threadPoolSize", 0, // switch to synchronous mode for unit test
        "allowedRunMode", new String[0]); // support all run modes (unit tests use 'publish' by default)

    // dynamic media support service
    context.registerInjectActivateService(DynamicMediaSupportServiceImpl.class);

    // link handler
    context.registerInjectActivateService(LinkHandlerAdapterFactory.class);
    context.registerInjectActivateService(DefaultLinkHandlerConfig.class);
    context.registerInjectActivateService(ImageMapLinkResolverImpl.class);

    // rich text handler
    context.registerInjectActivateService(DefaultRichTextHandlerConfig.class);

  }

}
