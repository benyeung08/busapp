// 安全版 proxy，只允許香港交通 API + 10秒快取
const ALLOW = [
  'data.etabus.gov.hk',
  'rt.data.gov.hk',
  'data.etagmb.gov.hk',
  'www.mtr.com.hk',
  'raw.githubusercontent.com'
];
const CACHE = new Map();

export default async function handler(req, res) {
  const target = req.query.url;
  if (!target) return res.status(400).json({error: 'missing url'});
  
  try {
    const u = new URL(target);
    if (!ALLOW.some(d => u.hostname.endsWith(d))) {
      return res.status(403).json({error: 'domain not allowed'});
    }

    const cacheKey = target;
    const cached = CACHE.get(cacheKey);
    if (cached && Date.now() - cached.t < 10000) {
      res.setHeader('X-Cache', 'HIT');
      return res.status(200).send(cached.data);
    }

    const r = await fetch(target, { headers: { 'User-Agent': 'hketaapp/1.6' } });
    const data = await r.text();
    CACHE.set(cacheKey, { t: Date.now(), data });
    
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Cache-Control', 's-maxage=10, stale-while-revalidate=30');
    res.status(r.status).send(data);
  } catch (e) {
    res.status(500).json({error: e.message});
  }
}
