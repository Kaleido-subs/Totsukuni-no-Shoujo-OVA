import myaa.subkt.ass.*
import myaa.subkt.tasks.*
import myaa.subkt.tasks.Mux.*
import myaa.subkt.tasks.Nyaa.*
import java.awt.Color
import java.time.*

plugins {
    id("myaa.subkt")
}

subs {
    readProperties("sub.properties")
    episodes(getList("episodes"))

    merge {
        from(get("dialogue"))
        from(getList("TS"))
    }

    chapters {
        from(merge.item())
        chapterMarker("chapter")
    }


    mux {
        title(get("muxtitle"))

		from(get("premux")) {
			video {
				lang("jpn")
				default(true)
			}
			audio {
				lang("jpn")
				default(true)
			}
            includeChapters(false)
			attachments { include(false) }
		}

		from(merge.item()) {
			tracks {
				lang("eng")
                name(get("group"))
				default(true)
				forced(true)
				compression(CompressionType.ZLIB)
			}
		}

        chapters(chapters.item()) { lang("eng") }

        attach(get("fonts")) {
            includeExtensions("ttf", "otf")
        }

        out(get("muxout"))
    }
}
