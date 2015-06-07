package com.hivesys.core.es.Carrot;

public final class ClusterResult {
    public final long took;
    public final boolean timed_out;
    public final _shards _shards;
    public final Hits hits;
    public final Cluster clusters[];
    public final Info info;

    public ClusterResult(long took, boolean timed_out, _shards _shards, Hits hits, Cluster[] clusters, Info info){
        this.took = took;
        this.timed_out = timed_out;
        this._shards = _shards;
        this.hits = hits;
        this.clusters = clusters;
        this.info = info;
    }

    public static final class _shards {
        public final long total;
        public final long successful;
        public final long failed;

        public _shards(long total, long successful, long failed){
            this.total = total;
            this.successful = successful;
            this.failed = failed;
        }
    }

    public static final class Hits {
        public final long total;
        public final double max_score;
        public final Hit hits[];

        public Hits(long total, double max_score, Hit[] hits){
            this.total = total;
            this.max_score = max_score;
            this.hits = hits;
        }

        public static final class Hit {
    
            public Hit(){
            }
        }
    }

    public static final class Cluster {
        public final Long id;
        public final Double score;
        public final String label;
        public final String[] phrases;
        public final String[] documents;

        public Cluster(Long id, Double score, String label, String[] phrases, String[] documents){
            this.id = id;
            this.score = score;
            this.label = label;
            this.phrases = phrases;
            this.documents = documents;
        }
    }

    public static final class Info {
        public final String algorithm;
        public final String search;
        public final String clustering;
        public final String total;
        public final String include;
        public final String max;

        public Info(String algorithm, String search, String clustering, String total, String include, String max){
            this.algorithm = algorithm;
            this.search = search;
            this.clustering = clustering;
            this.total = total;
            this.include = include;
            this.max = max;
        }
    }
}